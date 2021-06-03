import dayjs from 'dayjs';
import { IPractitioner } from 'app/shared/model/practitioner.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IListRoleCodeEnum } from 'app/shared/model/list-role-code-enum.model';
import { IListSpecialtyEnum } from 'app/shared/model/list-specialty-enum.model';

export interface IPractitionerRole {
  id?: number;
  guid?: string | null;
  forceId?: string | null;
  start?: string | null;
  end?: string | null;
  practitioner?: IPractitioner | null;
  organization?: IOrganization | null;
  codes?: IListRoleCodeEnum[] | null;
  specialties?: IListSpecialtyEnum[] | null;
}

export const defaultValue: Readonly<IPractitionerRole> = {};
