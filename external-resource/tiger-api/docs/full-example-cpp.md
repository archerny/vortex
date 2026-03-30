# 完整使用示例

动量策略

[](./full-example-cpp.md#%E5%8A%A8%E9%87%8F%E7%AD%96%E7%95%A5)

-------------------------------------------------------------------------------------------------

**注意：本策略仅为使用接口提供示例用法，比如查询行情，下单等，策略本身未经验证，不构成投资建议。**

该策略基本思路为"过去一段时间收益较高的资产，在未来仍将延续原有趋势，可能会获得较高的收益"，选股所用股票池为纳斯达克100指数成份股。

具体实现过程为：定期运行策略，每次选取股票池中周期内涨幅最高的若干股票，作为本次调仓的目标股票买入持有，对先前持仓中未入选的股票进行平仓。

代码如下：

C++

    #include <iostream>
    #include <vector>
    #include <map>
    #include <set>
    #include <string>
    #include <algorithm>
    #include <numeric>
    #include <cmath>
    #include <thread>
    #include <chrono>
    #include <ctime>
    
    #include "tigerapi/quote_client.h"
    #include "tigerapi/trade_client.h"
    #include "tigerapi/client_config.h"
    #include "tigerapi/contract_util.h"
    #include "tigerapi/order_util.h"
    
    using namespace TIGER_API;
    using namespace web::json;
    
    // 纳斯达克100指数成分股 Components of Nasdaq-100
    static const std::vector<utility::string_t> UNIVERSE_NDX = {
        U("AAPL"), U("ADBE"), U("ADI"), U("ADP"), U("ADSK"), U("AEP"), U("ALGN"), U("AMAT"), U("AMD"), U("AMGN"),
        U("AMZN"), U("ANSS"), U("ASML"), U("ATVI"), U("AVGO"), U("BIDU"), U("BIIB"), U("BKNG"), U("CDNS"), U("CDW"),
        U("CERN"), U("CHKP"), U("CHTR"), U("CMCSA"), U("COST"), U("CPRT"), U("CRWD"), U("CSCO"), U("CSX"), U("CTAS"),
        U("CTSH"), U("DLTR"), U("DOCU"), U("DXCM"), U("EA"), U("EBAY"), U("EXC"), U("FAST"), U("FB"), U("FISV"),
        U("FOX"), U("GILD"), U("GOOG"), U("HON"), U("IDXX"), U("ILMN"), U("INCY"), U("INTC"), U("INTU"), U("ISRG"),
        U("JD"), U("KDP"), U("KHC"), U("KLAC"), U("LRCX"), U("LULU"), U("MAR"), U("MCHP"), U("MDLZ"), U("MELI"),
        U("MNST"), U("MRNA"), U("MRVL"), U("MSFT"), U("MTCH"), U("MU"), U("NFLX"), U("NTES"), U("NVDA"), U("NXPI"),
        U("OKTA"), U("ORLY"), U("PAYX"), U("PCAR"), U("PDD"), U("PEP"), U("PTON"), U("PYPL"), U("QCOM"), U("REGN"),
        U("ROST"), U("SBUX"), U("SGEN"), U("SIRI"), U("SNPS"), U("SPLK"), U("SWKS"), U("TCOM"), U("TEAM"), U("TMUS"),
        U("TSLA"), U("TXN"), U("VRSK"), U("VRSN"), U("VRTX"), U("WBA"), U("WDAY"), U("XEL"), U("XLNX"), U("ZM")
    };
    
    // 持仓股票个数
    static const int HOLDING_NUM = 5;
    // 订单检查次数
    static const int ORDERS_CHECK_MAX_TIMES = 10;
    // 获取行情每次请求symbol个数
    static const int REQUEST_SIZE = 50;
    // 计算动量的时间周期（天数）
    static const int MOMENTUM_PERIOD = 30;
    
    // 获取当前毫秒时间戳
    int64_t now_ms() {
        auto now = std::chrono::system_clock::now();
        return std::chrono::duration_cast<std::chrono::milliseconds>(now.time_since_epoch()).count();
    }
    
    // 获取指定天数前的毫秒时间戳
    int64_t time_before_days(int days) {
        auto now = std::chrono::system_clock::now();
        auto target = now - std::chrono::hours(24 * days);
        return std::chrono::duration_cast<std::chrono::milliseconds>(target.time_since_epoch()).count();
    }
    
    /**
     * 动量策略类
     */
    class MomentumStrategy {
    public:
        MomentumStrategy(ClientConfig& config)
            : quote_client_(config), trade_client_(config), config_(config) {}
    
        void run() {
            // 查询行情权限
            value perms = quote_client_.grab_quote_permission();
            std::cout << "Quote permissions: " << perms.serialize() << std::endl;
    
            // 1. 按动量筛选股票
            screen_stocks();
    
            // 2. 调仓
            rebalance_portfolio();
        }
    
    private:
        QuoteClient quote_client_;
        TradeClient trade_client_;
        ClientConfig& config_;
        std::vector<utility::string_t> selected_symbols_;
    
        /**
         * 按动量筛选股票
         * 选取周期内涨幅最高的若干股票
         */
        void screen_stocks() {
            std::cout << "=== 开始筛选股票 ===" << std::endl;
    
            // 用于存储每只股票的动量（涨幅）
            std::map<utility::string_t, double> momentum_map;
    
            // 分批获取历史K线数据
            for (size_t i = 0; i < UNIVERSE_NDX.size(); i += REQUEST_SIZE) {
                value symbols = value::array();
                for (size_t j = i; j < std::min(i + (size_t)REQUEST_SIZE, UNIVERSE_NDX.size()); j++) {
                    symbols[j - i] = value::string(UNIVERSE_NDX[j]);
                }
    
                // 获取日K线数据，获取足够的天数来计算动量
                value bars = quote_client_.get_bars(symbols, U("day"), MOMENTUM_PERIOD + 5);
    
                // 解析K线数据，计算每只股票的动量
                if (bars.is_array()) {
                    for (size_t k = 0; k < bars.size(); k++) {
                        auto& bar = bars[k];
                        if (bar.has_field(U("symbol")) && bar.has_field(U("close"))) {
                            utility::string_t symbol = bar[U("symbol")].as_string();
                            // 收集每只股票的收盘价
                            if (momentum_map.find(symbol) == momentum_map.end()) {
                                // 记录最早的收盘价（用于计算区间涨幅）
                                momentum_map[symbol] = bar[U("close")].as_double();
                            }
                        }
                    }
                }
    
                // 控制请求频率
                std::this_thread::sleep_for(std::chrono::milliseconds(500));
            }
    
            // 获取最新价格，计算涨幅
            std::vector<std::pair<utility::string_t, double>> momentum_list;
            for (size_t i = 0; i < UNIVERSE_NDX.size(); i += REQUEST_SIZE) {
                value symbols = value::array();
                for (size_t j = i; j < std::min(i + (size_t)REQUEST_SIZE, UNIVERSE_NDX.size()); j++) {
                    symbols[j - i] = value::string(UNIVERSE_NDX[j]);
                }
                value briefs = quote_client_.get_brief(symbols);
                if (briefs.is_array()) {
                    for (size_t k = 0; k < briefs.size(); k++) {
                        auto& item = briefs[k];
                        if (item.has_field(U("symbol")) && item.has_field(U("latestPrice"))) {
                            utility::string_t sym = item[U("symbol")].as_string();
                            double latest = item[U("latestPrice")].as_double();
                            if (momentum_map.count(sym) && momentum_map[sym] > 0) {
                                double change = (latest - momentum_map[sym]) / momentum_map[sym];
                                momentum_list.push_back({sym, change});
                            }
                        }
                    }
                }
                std::this_thread::sleep_for(std::chrono::milliseconds(500));
            }
    
            // 按涨幅降序排列，选取涨幅最高的 HOLDING_NUM 只股票
            std::sort(momentum_list.begin(), momentum_list.end(),
                      [](const auto& a, const auto& b) { return a.second > b.second; });
    
            selected_symbols_.clear();
            for (int i = 0; i < std::min(HOLDING_NUM, (int)momentum_list.size()); i++) {
                selected_symbols_.push_back(momentum_list[i].first);
                std::cout << "Selected: " << momentum_list[i].first
                          << " momentum: " << momentum_list[i].second << std::endl;
            }
        }
    
        /**
         * 调仓
         * 先将本次选股未选中但在持仓中的股票进行平仓，然后将选中的股票按照等权重买入
         */
        void rebalance_portfolio() {
            std::cout << "=== 开始调仓 ===" << std::endl;
    
            // 获取当前持仓
            value positions_json = trade_client_.get_positions(U("STK"), U("US"));
            std::map<utility::string_t, int> positions;  // symbol -> quantity
            if (positions_json.is_array()) {
                for (size_t i = 0; i < positions_json.size(); i++) {
                    auto& pos = positions_json[i];
                    if (pos.has_field(U("symbol")) && pos.has_field(U("quantity"))) {
                        positions[pos[U("symbol")].as_string()] = pos[U("quantity")].as_integer();
                    }
                }
            }
    
            // 找出需要平仓的股票：在持仓中但不在本次选股结果中
            std::set<utility::string_t> selected_set(selected_symbols_.begin(), selected_symbols_.end());
            std::vector<utility::string_t> need_close_symbols;
            for (auto& [symbol, qty] : positions) {
                if (selected_set.find(symbol) == selected_set.end()) {
                    need_close_symbols.push_back(symbol);
                }
            }
    
            // 平仓未选中的股票
            if (!need_close_symbols.empty()) {
                // 获取最新价格用于下限价单
                value close_symbols_arr = value::array();
                for (size_t i = 0; i < need_close_symbols.size(); i++) {
                    close_symbols_arr[i] = value::string(need_close_symbols[i]);
                }
                value close_briefs = quote_client_.get_brief(close_symbols_arr);
                std::map<utility::string_t, double> close_prices;
                if (close_briefs.is_array()) {
                    for (size_t i = 0; i < close_briefs.size(); i++) {
                        auto& item = close_briefs[i];
                        if (item.has_field(U("symbol")) && item.has_field(U("latestPrice"))) {
                            close_prices[item[U("symbol")].as_string()] = item[U("latestPrice")].as_double();
                        }
                    }
                }
    
                std::vector<Order> sell_orders;
                for (auto& symbol : need_close_symbols) {
                    int quantity = positions[symbol];
                    if (quantity == 0) continue;
    
                    Contract contract = ContractUtil::stock_contract(symbol, U("USD"));
                    utility::string_t action = (quantity > 0) ? U("SELL") : U("BUY");
                    Order order = OrderUtil::limit_order(
                        config_.account, contract, action,
                        std::abs(quantity), close_prices[symbol]
                    );
                    sell_orders.push_back(order);
                    std::cout << "平仓: " << action << " " << symbol
                              << " quantity=" << std::abs(quantity)
                              << " price=" << close_prices[symbol] << std::endl;
                }
                execute_orders(sell_orders);
            }
    
            // 获取账户资产信息，计算可用于买入的金额
            value asset_json = trade_client_.get_prime_assets();
            double equity_with_loan = 0;
            double overnight_liquidation = 0;
            if (asset_json.has_field(U("equityWithLoan"))) {
                equity_with_loan = asset_json[U("equityWithLoan")].as_double();
            }
            if (asset_json.has_field(U("overnightLiquidation"))) {
                overnight_liquidation = asset_json[U("overnightLiquidation")].as_double();
            }
    
            // 调仓后目标隔夜剩余流动性
            // 隔夜剩余流动性比例 = 隔夜剩余流动性 / 含贷款价值总权益
            double target_ratio = 0.6;
            double target_overnight_liquidation = equity_with_loan * target_ratio;
            double adjust_value = overnight_liquidation - target_overnight_liquidation;
            if (adjust_value <= 0) {
                std::cout << "流动性不足，无法买入" << std::endl;
                return;
            }
    
            // 获取选中股票的最新价格
            value selected_arr = value::array();
            for (size_t i = 0; i < selected_symbols_.size(); i++) {
                selected_arr[i] = value::string(selected_symbols_[i]);
            }
            value buy_briefs = quote_client_.get_brief(selected_arr);
            std::map<utility::string_t, double> buy_prices;
            if (buy_briefs.is_array()) {
                for (size_t i = 0; i < buy_briefs.size(); i++) {
                    auto& item = buy_briefs[i];
                    if (item.has_field(U("symbol")) && item.has_field(U("latestPrice"))) {
                        buy_prices[item[U("symbol")].as_string()] = item[U("latestPrice")].as_double();
                    }
                }
            }
    
            // 按持股数量等权重持仓 equal weight
            double weight = 1.0 / selected_symbols_.size();
            std::vector<Order> buy_orders;
            for (auto& symbol : selected_symbols_) {
                double price = buy_prices[symbol];
                if (price <= 0) continue;
                int quantity = static_cast<int>(adjust_value * weight / price);
                if (quantity <= 0) continue;
    
                Contract contract = ContractUtil::stock_contract(symbol, U("USD"));
                Order order = OrderUtil::limit_order(
                    config_.account, contract, U("BUY"),
                    quantity, price
                );
                order.time_in_force = U("GTC");  // 撤销前有效
                buy_orders.push_back(order);
                std::cout << "买入: BUY " << symbol
                          << " quantity=" << quantity
                          << " price=" << price << std::endl;
            }
            execute_orders(buy_orders);
        }
    
        /**
         * 执行订单列表：下单、检查成交、改单、撤单
         */
        void execute_orders(std::vector<Order>& orders) {
            std::map<int64_t, Order> local_orders;
            for (auto& order : orders) {
                try {
                    value result = trade_client_.place_order(order);
                    std::cout << "下单成功: " << order.contract.symbol
                              << " id=" << order.id << std::endl;
                    local_orders[order.id] = order;
                } catch (const std::exception& e) {
                    std::cerr << "下单失败: " << order.contract.symbol
                              << " error=" << e.what() << std::endl;
                }
            }
    
            // 等待订单成交
            std::this_thread::sleep_for(std::chrono::seconds(20));
    
            for (int i = 0; i <= ORDERS_CHECK_MAX_TIMES; i++) {
                std::cout << "检查订单状态，第 " << i << " 次" << std::endl;
    
                value open_orders = trade_client_.get_open_orders(
                    U("STK"), U("US"),
                    time_before_days(1), now_ms()
                );
    
                if (!open_orders.is_array() || open_orders.size() == 0) {
                    std::cout << "所有订单已成交" << std::endl;
                    break;
                }
    
                // 检查一定次数后如果还未成交，进行一次改单，修改限价为最新价格
                if (i == ORDERS_CHECK_MAX_TIMES / 2) {
                    for (size_t j = 0; j < open_orders.size(); j++) {
                        auto& open_order = open_orders[j];
                        if (open_order.has_field(U("symbol")) && open_order.has_field(U("id"))) {
                            utility::string_t sym = open_order[U("symbol")].as_string();
                            int64_t order_id = open_order[U("id")].as_number().to_int64();
    
                            value sym_arr = value::array();
                            sym_arr[0] = value::string(sym);
                            value brief = quote_client_.get_brief(sym_arr);
                            if (brief.is_array() && brief.size() > 0 &&
                                brief[0].has_field(U("latestPrice"))) {
                                double new_price = brief[0][U("latestPrice")].as_double();
                                try {
                                    trade_client_.modify_order(order_id, new_price);
                                    std::cout << "改单: id=" << order_id
                                              << " symbol=" << sym
                                              << " new_price=" << new_price << std::endl;
                                } catch (const std::exception& e) {
                                    std::cerr << "改单失败: id=" << order_id
                                              << " error=" << e.what() << std::endl;
                                }
                            }
                        }
                    }
                }
    
                // 如果达到最大检查次数还未成交，则进行撤单
                if (i >= ORDERS_CHECK_MAX_TIMES) {
                    for (size_t j = 0; j < open_orders.size(); j++) {
                        auto& open_order = open_orders[j];
                        if (open_order.has_field(U("id"))) {
                            int64_t order_id = open_order[U("id")].as_number().to_int64();
                            try {
                                trade_client_.cancel_order(order_id);
                                std::cout << "撤单: id=" << order_id << std::endl;
                            } catch (const std::exception& e) {
                                std::cerr << "撤单失败: id=" << order_id
                                          << " error=" << e.what() << std::endl;
                            }
                        }
                    }
                }
    
                std::this_thread::sleep_for(std::chrono::seconds(10));
            }
    
            // 打印已成交订单信息
            value filled = trade_client_.get_filled_orders(
                U("STK"), U("US"),
                time_before_days(1), now_ms()
            );
            if (filled.is_array()) {
                std::cout << "已成交订单:" << std::endl;
                for (size_t i = 0; i < filled.size(); i++) {
                    std::cout << "  " << filled[i].serialize() << std::endl;
                }
            }
        }
    };
    
    int main() {
        // 初始化配置
        ClientConfig config(false, U("/path/to/your/properties/"));
    
        // 创建策略并运行
        MomentumStrategy strategy(config);
        strategy.run();
    
        return 0;
    }
