import { IClaimResponse } from 'app/shared/model/claim-response.model';

export interface ITotal {
  id?: number;
  category?: string | null;
  amount?: number | null;
  claimResponse?: IClaimResponse | null;
}

export const defaultValue: Readonly<ITotal> = {};
