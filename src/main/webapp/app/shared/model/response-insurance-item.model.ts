import { IInsuranceBenefit } from 'app/shared/model/insurance-benefit.model';
import { IResponseInsurance } from 'app/shared/model/response-insurance.model';

export interface IResponseInsuranceItem {
  id?: number;
  category?: string | null;
  excluded?: boolean | null;
  name?: string | null;
  description?: string | null;
  network?: string | null;
  unit?: string | null;
  term?: string | null;
  benefits?: IInsuranceBenefit[] | null;
  responseInsurance?: IResponseInsurance | null;
}

export const defaultValue: Readonly<IResponseInsuranceItem> = {
  excluded: false,
};
