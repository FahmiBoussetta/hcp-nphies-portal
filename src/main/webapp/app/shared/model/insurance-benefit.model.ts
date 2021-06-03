import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';

export interface IInsuranceBenefit {
  id?: number;
  allowed?: string | null;
  used?: string | null;
  responseInsuranceItem?: IResponseInsuranceItem | null;
}

export const defaultValue: Readonly<IInsuranceBenefit> = {};
