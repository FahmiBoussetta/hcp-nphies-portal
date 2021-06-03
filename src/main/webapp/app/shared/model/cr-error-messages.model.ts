import { IClaimResponse } from 'app/shared/model/claim-response.model';

export interface ICRErrorMessages {
  id?: number;
  message?: string | null;
  claimResponse?: IClaimResponse | null;
}

export const defaultValue: Readonly<ICRErrorMessages> = {};
