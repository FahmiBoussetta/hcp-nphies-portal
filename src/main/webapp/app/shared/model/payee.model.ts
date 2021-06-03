import { IPatient } from 'app/shared/model/patient.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { PayeeTypeEnum } from 'app/shared/model/enumerations/payee-type-enum.model';

export interface IPayee {
  id?: number;
  type?: PayeeTypeEnum;
  partyPatient?: IPatient | null;
  partyOrganization?: IOrganization | null;
}

export const defaultValue: Readonly<IPayee> = {};
