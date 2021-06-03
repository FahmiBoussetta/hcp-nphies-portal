import { IAdjudicationDetailNotes } from 'app/shared/model/adjudication-detail-notes.model';
import { IAdjudication } from 'app/shared/model/adjudication.model';
import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';

export interface IAdjudicationDetailItem {
  id?: number;
  sequence?: number;
  notes?: IAdjudicationDetailNotes[] | null;
  adjudications?: IAdjudication[] | null;
  subDetails?: IAdjudicationSubDetailItem[] | null;
  adjudicationItem?: IAdjudicationItem | null;
}

export const defaultValue: Readonly<IAdjudicationDetailItem> = {};
