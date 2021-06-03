export interface IQuantity {
  id?: number;
  value?: number | null;
  unit?: string | null;
}

export const defaultValue: Readonly<IQuantity> = {};
