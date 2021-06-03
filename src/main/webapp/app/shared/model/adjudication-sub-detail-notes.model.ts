import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';

export interface IAdjudicationSubDetailNotes {
  id?: number;
  note?: string | null;
  adjudicationSubDetailItem?: IAdjudicationSubDetailItem | null;
}

export const defaultValue: Readonly<IAdjudicationSubDetailNotes> = {};
