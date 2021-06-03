import { IGivens } from 'app/shared/model/givens.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IPractitioner } from 'app/shared/model/practitioner.model';

export interface IHumanName {
  id?: number;
  family?: string;
  givens?: IGivens[] | null;
  patient?: IPatient | null;
  practitioner?: IPractitioner | null;
}

export const defaultValue: Readonly<IHumanName> = {};
