import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';
import { getEntities as getPaymentReconciliations } from 'app/entities/payment-reconciliation/payment-reconciliation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment-notice.reducer';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentNoticeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const paymentReconciliations = useAppSelector(state => state.paymentReconciliation.entities);
  const paymentNoticeEntity = useAppSelector(state => state.paymentNotice.entity);
  const loading = useAppSelector(state => state.paymentNotice.loading);
  const updating = useAppSelector(state => state.paymentNotice.updating);
  const updateSuccess = useAppSelector(state => state.paymentNotice.updateSuccess);

  const handleClose = () => {
    props.history.push('/payment-notice');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPaymentReconciliations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.paymentDate = convertDateTimeToServer(values.paymentDate);

    const entity = {
      ...paymentNoticeEntity,
      ...values,
      payment: paymentReconciliations.find(it => it.id.toString() === values.paymentId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          paymentDate: displayDefaultDateTime(),
        }
      : {
          ...paymentNoticeEntity,
          paymentDate: convertDateTimeFromServer(paymentNoticeEntity.paymentDate),
          paymentStatus: 'Paid',
          paymentId: paymentNoticeEntity?.payment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.paymentNotice.home.createOrEditLabel" data-cy="PaymentNoticeCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.paymentNotice.home.createOrEditLabel">Create or edit a PaymentNotice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="payment-notice-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.guid')}
                id="payment-notice-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.parsed')}
                id="payment-notice-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.identifier')}
                id="payment-notice-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.paymentDate')}
                id="payment-notice-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.amount')}
                id="payment-notice-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentNotice.paymentStatus')}
                id="payment-notice-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                <option value="Paid">{translate('hcpNphiesPortalApp.PaymentStatusEnum.Paid')}</option>
                <option value="Cleared">{translate('hcpNphiesPortalApp.PaymentStatusEnum.Cleared')}</option>
              </ValidatedField>
              <ValidatedField
                id="payment-notice-payment"
                name="paymentId"
                data-cy="payment"
                label={translate('hcpNphiesPortalApp.paymentNotice.payment')}
                type="select"
              >
                <option value="" key="0" />
                {paymentReconciliations
                  ? paymentReconciliations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment-notice" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PaymentNoticeUpdate;
