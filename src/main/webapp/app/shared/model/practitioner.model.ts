import { IHumanName } from 'app/shared/model/human-name.model';
import { AdministrativeGenderEnum } from 'app/shared/model/enumerations/administrative-gender-enum.model';

export interface IPractitioner {
  id?: number;
  guid?: string | null;
  forceId?: string | null;
  practitionerLicense?: string;
  gender?: AdministrativeGenderEnum | null;
  names?: IHumanName[] | null;
}

export const defaultValue: Readonly<IPractitioner> = {};
