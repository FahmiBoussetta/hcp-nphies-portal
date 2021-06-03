import { IHumanName } from 'app/shared/model/human-name.model';
import { IOrganization } from 'app/shared/model/organization.model';

export interface IContact {
  id?: number;
  phone?: string | null;
  email?: string | null;
  mobile?: string | null;
  url?: string | null;
  name?: IHumanName | null;
  organization?: IOrganization | null;
}

export const defaultValue: Readonly<IContact> = {};
