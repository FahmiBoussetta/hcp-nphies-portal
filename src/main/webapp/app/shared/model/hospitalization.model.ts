import { IOrganization } from 'app/shared/model/organization.model';
import { AdmitSourceEnum } from 'app/shared/model/enumerations/admit-source-enum.model';
import { ReAdmissionEnum } from 'app/shared/model/enumerations/re-admission-enum.model';
import { DischargeDispositionEnum } from 'app/shared/model/enumerations/discharge-disposition-enum.model';

export interface IHospitalization {
  id?: number;
  admitSource?: AdmitSourceEnum | null;
  reAdmission?: ReAdmissionEnum | null;
  dischargeDisposition?: DischargeDispositionEnum | null;
  origin?: IOrganization | null;
}

export const defaultValue: Readonly<IHospitalization> = {};
