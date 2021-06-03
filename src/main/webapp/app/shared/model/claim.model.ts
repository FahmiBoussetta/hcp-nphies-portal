import dayjs from 'dayjs';
import { IEncounter } from 'app/shared/model/encounter.model';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { IPayee } from 'app/shared/model/payee.model';
import { ILocation } from 'app/shared/model/location.model';
import { IAccident } from 'app/shared/model/accident.model';
import { IDiagnosis } from 'app/shared/model/diagnosis.model';
import { IInsurance } from 'app/shared/model/insurance.model';
import { IItem } from 'app/shared/model/item.model';
import { IClaimErrorMessages } from 'app/shared/model/claim-error-messages.model';
import { IRelated } from 'app/shared/model/related.model';
import { ICareTeam } from 'app/shared/model/care-team.model';
import { ISupportingInfo } from 'app/shared/model/supporting-info.model';
import { Use } from 'app/shared/model/enumerations/use.model';
import { ClaimTypeEnum } from 'app/shared/model/enumerations/claim-type-enum.model';
import { ClaimSubTypeEnum } from 'app/shared/model/enumerations/claim-sub-type-enum.model';
import { PriorityEnum } from 'app/shared/model/enumerations/priority-enum.model';
import { FundsReserveEnum } from 'app/shared/model/enumerations/funds-reserve-enum.model';

export interface IClaim {
  id?: number;
  guid?: string | null;
  isQueued?: boolean | null;
  parsed?: string | null;
  identifier?: string | null;
  use?: Use;
  type?: ClaimTypeEnum;
  subType?: ClaimSubTypeEnum | null;
  eligibilityOffline?: string | null;
  eligibilityOfflineDate?: string | null;
  authorizationOfflineDate?: string | null;
  billableStart?: string | null;
  billableEnd?: string | null;
  priority?: PriorityEnum;
  fundsReserve?: FundsReserveEnum | null;
  encounter?: IEncounter | null;
  eligibilityResponse?: ICoverageEligibilityResponse | null;
  patient?: IPatient | null;
  provider?: IOrganization | null;
  insurer?: IOrganization | null;
  prescription?: IReferenceIdentifier | null;
  originalPrescription?: IReferenceIdentifier | null;
  payee?: IPayee | null;
  referral?: IReferenceIdentifier | null;
  facility?: ILocation | null;
  accident?: IAccident | null;
  diagnoses?: IDiagnosis[] | null;
  insurances?: IInsurance[] | null;
  items?: IItem[] | null;
  errors?: IClaimErrorMessages[] | null;
  relateds?: IRelated[] | null;
  careTeams?: ICareTeam[] | null;
  supportingInfos?: ISupportingInfo[] | null;
}

export const defaultValue: Readonly<IClaim> = {
  isQueued: false,
};
