import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { RoleCodeEnum } from 'app/shared/model/enumerations/role-code-enum.model';

export interface IListRoleCodeEnum {
  id?: number;
  r?: RoleCodeEnum | null;
  practitionerRole?: IPractitionerRole | null;
}

export const defaultValue: Readonly<IListRoleCodeEnum> = {};
