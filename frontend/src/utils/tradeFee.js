/** Use the same exact decimal text for display and editing. */
export const feeText = (record, field = 'fee') =>
  record[`${field}Exact`] ?? (record[field] == null ? null : String(record[field]));

export const feeForInput = (record) => feeText(record) ?? '0';

export const hasFee = (record, field) => /[1-9]/.test((feeText(record, field) ?? '').split(/[eE]/)[0]);

// Compare decimal coefficients at the same scale without a floating-point conversion.
export const compareFees = (left, right) => {
  const parts = (text) => {
    const [mantissa, exponent = '0'] = text.toLowerCase().split('e');
    const [integer, fraction = ''] = mantissa.split('.');
    return { coefficient: BigInt(integer + fraction), scale: fraction.length - Number(exponent) };
  };
  const a = parts(feeForInput(left));
  const b = parts(feeForInput(right));
  const scale = Math.max(a.scale, b.scale);
  const aValue = a.coefficient * 10n ** BigInt(scale - a.scale);
  const bValue = b.coefficient * 10n ** BigInt(scale - b.scale);
  return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
};
