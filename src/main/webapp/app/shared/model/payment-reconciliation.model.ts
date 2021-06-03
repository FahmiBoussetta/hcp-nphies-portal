import dayjs from 'dayjs';
import { IOrganization } from 'app/shared/model/organization.model';
import { IReconciliationDetailItem } from 'app/shared/model/reconciliation-detail-item.model';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';

export interface IPaymentReconciliation {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  periodStart?: string | null;
  periodEnd?: string | null;
  outcome?: string | null;
  disposition?: string | null;
  paymentAmount?: number | null;
  paymentIdentifier?: string | null;
  paymentIssuer?: IOrganization | null;
  details?: IReconciliationDetailItem[] | null;
  paymentNotice?: IPaymentNotice | null;
}

export const defaultValue: Readonly<IPaymentReconciliation> = {};
