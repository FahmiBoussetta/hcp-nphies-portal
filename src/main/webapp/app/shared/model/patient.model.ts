import dayjs from 'dayjs';
import { IContact } from 'app/shared/model/contact.model';
import { IAddress } from 'app/shared/model/address.model';
import { IHumanName } from 'app/shared/model/human-name.model';
import { ReligionEnum } from 'app/shared/model/enumerations/religion-enum.model';
import { AdministrativeGenderEnum } from 'app/shared/model/enumerations/administrative-gender-enum.model';
import { MaritalStatusEnum } from 'app/shared/model/enumerations/marital-status-enum.model';

export interface IPatient {
  id?: number;
  guid?: string | null;
  forceId?: string | null;
  residentNumber?: string | null;
  passportNumber?: string | null;
  nationalHealthId?: string | null;
  iqama?: string | null;
  religion?: ReligionEnum | null;
  gender?: AdministrativeGenderEnum | null;
  birthDate?: string | null;
  deceasedDate?: string | null;
  maritalStatus?: MaritalStatusEnum | null;
  contacts?: IContact | null;
  address?: IAddress | null;
  names?: IHumanName[] | null;
}

export const defaultValue: Readonly<IPatient> = {};
