import { IPractitioner } from 'app/shared/model/practitioner.model';
import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { IClaim } from 'app/shared/model/claim.model';
import { CareTeamRoleEnum } from 'app/shared/model/enumerations/care-team-role-enum.model';

export interface ICareTeam {
  id?: number;
  sequence?: number;
  role?: CareTeamRoleEnum;
  provider?: IPractitioner | null;
  providerRole?: IPractitionerRole | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<ICareTeam> = {};
