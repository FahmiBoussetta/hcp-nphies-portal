import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { ICovEliRespErrorMessages } from 'app/shared/model/cov-eli-resp-error-messages.model';
import { IResponseInsurance } from 'app/shared/model/response-insurance.model';

export interface ICoverageEligibilityResponse {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  outcome?: string | null;
  serviced?: string | null;
  servicedEnd?: string | null;
  disposition?: string | null;
  notInforceReason?: string | null;
  patient?: IPatient | null;
  insurer?: IOrganization | null;
  errors?: ICovEliRespErrorMessages[] | null;
  insurances?: IResponseInsurance[] | null;
}

export const defaultValue: Readonly<ICoverageEligibilityResponse> = {};
