export interface IAddress {
  id?: number;
  addressLine?: string;
  city?: string;
  district?: string | null;
  state?: string | null;
  postalCode?: string;
  country?: string | null;
}

export const defaultValue: Readonly<IAddress> = {};
