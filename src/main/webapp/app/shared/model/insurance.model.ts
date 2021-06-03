import { ICoverage } from 'app/shared/model/coverage.model';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { IClaim } from 'app/shared/model/claim.model';

export interface IInsurance {
  id?: number;
  sequence?: number;
  focal?: boolean;
  preAuthRef?: string | null;
  coverage?: ICoverage | null;
  claimResponse?: IClaimResponse | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<IInsurance> = {
  focal: false,
};
