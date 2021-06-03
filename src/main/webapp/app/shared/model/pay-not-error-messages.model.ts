import { IPaymentNotice } from 'app/shared/model/payment-notice.model';

export interface IPayNotErrorMessages {
  id?: number;
  message?: string | null;
  paymentNotice?: IPaymentNotice | null;
}

export const defaultValue: Readonly<IPayNotErrorMessages> = {};
