import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';

export interface IAdjudication {
  id?: number;
  category?: string;
  reason?: string | null;
  amount?: number;
  value?: number | null;
  adjudicationItem?: IAdjudicationItem | null;
  adjudicationDetailItem?: IAdjudicationDetailItem | null;
  adjudicationSubDetailItem?: IAdjudicationSubDetailItem | null;
}

export const defaultValue: Readonly<IAdjudication> = {};
