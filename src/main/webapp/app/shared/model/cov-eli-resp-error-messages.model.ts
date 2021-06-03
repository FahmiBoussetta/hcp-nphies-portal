import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';

export interface ICovEliRespErrorMessages {
  id?: number;
  message?: string | null;
  coverageEligibilityResponse?: ICoverageEligibilityResponse | null;
}

export const defaultValue: Readonly<ICovEliRespErrorMessages> = {};
