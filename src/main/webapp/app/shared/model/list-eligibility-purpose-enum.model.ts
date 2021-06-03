import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { EligibilityPurposeEnum } from 'app/shared/model/enumerations/eligibility-purpose-enum.model';

export interface IListEligibilityPurposeEnum {
  id?: number;
  erp?: EligibilityPurposeEnum | null;
  coverageEligibilityRequest?: ICoverageEligibilityRequest | null;
}

export const defaultValue: Readonly<IListEligibilityPurposeEnum> = {};
