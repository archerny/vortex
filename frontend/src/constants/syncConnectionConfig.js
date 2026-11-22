export const syncConnectionConfig = {
  ibkr: {
    label: 'IBKR',
    description: '在 IBKR Client Portal 创建 Trade Confirmation Flex Query，填写对应的 Token 和查询 ID。',
    fields: [
      { name: 'token', label: 'Flex Token', secret: true, maxLength: 4096 },
      { name: 'queryId', label: 'Trade Confirmation Query ID', pattern: /^[0-9]{1,64}$/, maxLength: 64 },
    ],
  },
  tiger: {
    label: 'Tiger',
    description: '填写 Tiger 开放平台的 Tiger ID和 PKCS#8 格式 RSA 私钥，资金账号使用账户管理中的账户 ID。',
    fields: [
      { name: 'tigerId', label: 'Tiger ID', maxLength: 4096 },
      { name: 'privateKey', label: 'RSA 私钥（PKCS#8）', secret: true, multiline: true, maxLength: 65536 },
    ],
  },
};
