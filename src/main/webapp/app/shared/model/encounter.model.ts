import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { EncounterClassEnum } from 'app/shared/model/enumerations/encounter-class-enum.model';
import { ServiceTypeEnum } from 'app/shared/model/enumerations/service-type-enum.model';
import { ActPriorityEnum } from 'app/shared/model/enumerations/act-priority-enum.model';

export interface IEncounter {
  id?: number;
  guid?: string | null;
  forceId?: string | null;
  identifier?: string | null;
  encounterClass?: EncounterClassEnum | null;
  start?: string | null;
  end?: string | null;
  serviceType?: ServiceTypeEnum | null;
  priority?: ActPriorityEnum | null;
  subject?: IPatient | null;
  hospitalization?: IHospitalization | null;
  serviceProvider?: IOrganization | null;
}

export const defaultValue: Readonly<IEncounter> = {};
