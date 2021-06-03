import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';

export interface IAdjudicationDetailNotes {
  id?: number;
  note?: string | null;
  adjudicationDetailItem?: IAdjudicationDetailItem | null;
}

export const defaultValue: Readonly<IAdjudicationDetailNotes> = {};
