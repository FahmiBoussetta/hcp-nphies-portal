import { IPatient } from 'app/shared/model/patient.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IClassComponent } from 'app/shared/model/class-component.model';
import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { CoverageTypeEnum } from 'app/shared/model/enumerations/coverage-type-enum.model';
import { RelationShipEnum } from 'app/shared/model/enumerations/relation-ship-enum.model';

export interface ICoverage {
  id?: number;
  guid?: string | null;
  forceId?: string | null;
  coverageType?: CoverageTypeEnum;
  subscriberId?: string | null;
  dependent?: string | null;
  relationShip?: RelationShipEnum;
  network?: string | null;
  subrogation?: boolean | null;
  subscriberPatient?: IPatient | null;
  beneficiary?: IPatient | null;
  payor?: IOrganization | null;
  classComponents?: IClassComponent[] | null;
  costToBeneficiaryComponents?: ICostToBeneficiaryComponent[] | null;
  coverageEligibilityRequest?: ICoverageEligibilityRequest | null;
}

export const defaultValue: Readonly<ICoverage> = {
  subrogation: false,
};
