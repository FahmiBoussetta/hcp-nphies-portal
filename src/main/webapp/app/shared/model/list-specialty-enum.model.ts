import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { SpecialtyEnum } from 'app/shared/model/enumerations/specialty-enum.model';

export interface IListSpecialtyEnum {
  id?: number;
  s?: SpecialtyEnum | null;
  practitionerRole?: IPractitionerRole | null;
}

export const defaultValue: Readonly<IListSpecialtyEnum> = {};
