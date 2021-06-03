import dayjs from 'dayjs';
import { ICoverage } from 'app/shared/model/coverage.model';
import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';

export interface IResponseInsurance {
  id?: number;
  notInforceReason?: string | null;
  inforce?: boolean | null;
  benefitStart?: string | null;
  benefitEnd?: string | null;
  coverage?: ICoverage | null;
  items?: IResponseInsuranceItem[] | null;
  coverageEligibilityResponse?: ICoverageEligibilityResponse | null;
}

export const defaultValue: Readonly<IResponseInsurance> = {
  inforce: false,
};
