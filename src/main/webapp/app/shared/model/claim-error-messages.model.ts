import { IClaim } from 'app/shared/model/claim.model';

export interface IClaimErrorMessages {
  id?: number;
  message?: string | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<IClaimErrorMessages> = {};
