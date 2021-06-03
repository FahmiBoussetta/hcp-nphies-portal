import { IAdjudicationSubDetailNotes } from 'app/shared/model/adjudication-sub-detail-notes.model';
import { IAdjudication } from 'app/shared/model/adjudication.model';
import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';

export interface IAdjudicationSubDetailItem {
  id?: number;
  sequence?: number;
  notes?: IAdjudicationSubDetailNotes[] | null;
  adjudications?: IAdjudication[] | null;
  adjudicationDetailItem?: IAdjudicationDetailItem | null;
}

export const defaultValue: Readonly<IAdjudicationSubDetailItem> = {};
