import { ICRErrorMessages } from 'app/shared/model/cr-error-messages.model';
import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { ITotal } from 'app/shared/model/total.model';

export interface IClaimResponse {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  outcome?: string | null;
  errors?: ICRErrorMessages[] | null;
  items?: IAdjudicationItem[] | null;
  totals?: ITotal[] | null;
}

export const defaultValue: Readonly<IClaimResponse> = {};
