import { IOrganization } from 'app/shared/model/organization.model';
import { LocationTypeEnum } from 'app/shared/model/enumerations/location-type-enum.model';

export interface ILocation {
  id?: number;
  guid?: string | null;
  identifier?: string | null;
  type?: LocationTypeEnum | null;
  managingOrganization?: IOrganization | null;
}

export const defaultValue: Readonly<ILocation> = {};
