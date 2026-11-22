package com.vortex.service;

import com.vortex.entity.BrokerAccount;
import com.vortex.repository.BrokerAccountRepository;
import com.vortex.repository.BrokerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BrokerAccountService {
    private final BrokerAccountRepository accounts;
    private final BrokerRepository brokers;
    public BrokerAccountService(BrokerAccountRepository accounts, BrokerRepository brokers) {
        this.accounts = accounts; this.brokers = brokers;
    }
    public List<BrokerAccount> list(Long brokerId) {
        return brokerId == null ? accounts.findAllByOrderByIdAsc() : accounts.findByBrokerIdOrderByIdAsc(brokerId);
    }
    public BrokerAccount get(Long id) {
        if (id == null) throw new IllegalArgumentException("请选择托管账户");
        return accounts.findById(id).orElseThrow(() -> new IllegalArgumentException("托管账户不存在"));
    }
    /** Old broker-only writes are unambiguous only while a broker has one account. */
    public BrokerAccount resolve(Long accountKey, Long brokerId) {
        if (accountKey != null) {
            BrokerAccount account = get(accountKey);
            if (brokerId != null && !Objects.equals(account.getBrokerId(), brokerId)) {
                throw new IllegalArgumentException("托管账户与券商不一致");
            }
            return account;
        }
        if (brokerId == null) throw new IllegalArgumentException("请选择托管账户");
        List<BrokerAccount> candidates = list(brokerId);
        if (candidates.size() != 1) throw new IllegalArgumentException("该券商存在多个账户或尚无账户，请明确选择托管账户");
        return candidates.get(0);
    }
    @Transactional
    public BrokerAccount create(BrokerAccount input) {
        if (input.getId() != null) throw new IllegalArgumentException("新建账户不能指定已有ID");
        validate(input, false);
        return accounts.saveAndFlush(input);
    }
    @Transactional
    public BrokerAccount update(Long id, BrokerAccount input) {
        BrokerAccount current = get(id);
        if (!Objects.equals(current.getBrokerId(), input.getBrokerId())) throw new IllegalArgumentException("账户所属券商创建后不能变更");
        boolean updateEmail = input.getEmail() != null;
        boolean updatePhone = input.getPhone() != null;
        boolean updateDescription = input.getDescription() != null;
        validate(input, current.getAccountId() == null);
        if (input.getAccountId() != null) {
            accounts.findByBrokerIdAndAccountId(input.getBrokerId(), input.getAccountId()).ifPresent(other -> {
                if (!Objects.equals(other.getId(), id)) throw new IllegalArgumentException("该券商下的账户ID已存在");
            });
        }
        current.setAccountId(input.getAccountId()); current.setAccountName(input.getAccountName()); current.setIsActive(input.getIsActive());
        if (updateEmail) current.setEmail(input.getEmail());
        if (updatePhone) current.setPhone(input.getPhone());
        if (updateDescription) current.setDescription(input.getDescription());
        return accounts.saveAndFlush(current);
    }
    @Transactional
    public void delete(Long id) { accounts.delete(get(id)); accounts.flush(); }
    private void validate(BrokerAccount input, boolean allowPendingIdentity) {
        if (input.getBrokerId() == null || !brokers.existsById(input.getBrokerId())) throw new IllegalArgumentException("请选择已有券商");
        String identity = input.getAccountId() == null ? "" : input.getAccountId().trim();
        if ((!allowPendingIdentity && identity.isBlank()) || identity.length() > 100 || identity.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("请输入真实账户ID，最多100字符且不能包含空白");
        }
        String name = input.getAccountName() == null ? "" : input.getAccountName().trim();
        if (name.isBlank() || name.length() > 100) throw new IllegalArgumentException("请输入账户名称，最多100字符");
        input.setAccountId(identity.isBlank() ? null : identity); input.setAccountName(name);
        input.setEmail(optionalText(input.getEmail(), 100, "邮箱"));
        input.setPhone(optionalText(input.getPhone(), 30, "手机号"));
        input.setDescription(optionalText(input.getDescription(), 200, "账户备注"));
        if (input.getIsActive() == null) throw new IllegalArgumentException("请选择账户启用状态");
    }
    private String optionalText(String value, int maximum, String label) {
        if (value == null) return null;
        String text = value.trim();
        if (text.length() > maximum) throw new IllegalArgumentException(label + "最多" + maximum + "字符");
        return text.isEmpty() ? null : text;
    }
}
