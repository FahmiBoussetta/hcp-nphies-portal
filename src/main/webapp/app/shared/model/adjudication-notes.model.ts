import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';

export interface IAdjudicationNotes {
  id?: number;
  note?: string | null;
  adjudicationItem?: IAdjudicationItem | null;
}

export const defaultValue: Readonly<IAdjudicationNotes> = {};
