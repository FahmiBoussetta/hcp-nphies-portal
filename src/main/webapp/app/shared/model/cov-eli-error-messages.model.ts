import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';

export interface ICovEliErrorMessages {
  id?: number;
  message?: string | null;
  coverageEligibilityRequest?: ICoverageEligibilityRequest | null;
}

export const defaultValue: Readonly<ICovEliErrorMessages> = {};
