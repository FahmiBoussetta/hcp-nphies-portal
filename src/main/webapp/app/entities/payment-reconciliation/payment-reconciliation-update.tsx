import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { getEntities as getPaymentNotices } from 'app/entities/payment-notice/payment-notice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment-reconciliation.reducer';
import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentReconciliationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const organizations = useAppSelector(state => state.organization.entities);
  const paymentNotices = useAppSelector(state => state.paymentNotice.entities);
  const paymentReconciliationEntity = useAppSelector(state => state.paymentReconciliation.entity);
  const loading = useAppSelector(state => state.paymentReconciliation.loading);
  const updating = useAppSelector(state => state.paymentReconciliation.updating);
  const updateSuccess = useAppSelector(state => state.paymentReconciliation.updateSuccess);

  const handleClose = () => {
    props.history.push('/payment-reconciliation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrganizations({}));
    dispatch(getPaymentNotices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.periodStart = convertDateTimeToServer(values.periodStart);
    values.periodEnd = convertDateTimeToServer(values.periodEnd);

    const entity = {
      ...paymentReconciliationEntity,
      ...values,
      paymentIssuer: organizations.find(it => it.id.toString() === values.paymentIssuerId.toString()),
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
          periodStart: displayDefaultDateTime(),
          periodEnd: displayDefaultDateTime(),
        }
      : {
          ...paymentReconciliationEntity,
          periodStart: convertDateTimeFromServer(paymentReconciliationEntity.periodStart),
          periodEnd: convertDateTimeFromServer(paymentReconciliationEntity.periodEnd),
          paymentIssuerId: paymentReconciliationEntity?.paymentIssuer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.paymentReconciliation.home.createOrEditLabel" data-cy="PaymentReconciliationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.home.createOrEditLabel">
              Create or edit a PaymentReconciliation
            </Translate>
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
                  id="payment-reconciliation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.value')}
                id="payment-reconciliation-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.system')}
                id="payment-reconciliation-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.parsed')}
                id="payment-reconciliation-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.periodStart')}
                id="payment-reconciliation-periodStart"
                name="periodStart"
                data-cy="periodStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.periodEnd')}
                id="payment-reconciliation-periodEnd"
                name="periodEnd"
                data-cy="periodEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.outcome')}
                id="payment-reconciliation-outcome"
                name="outcome"
                data-cy="outcome"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.disposition')}
                id="payment-reconciliation-disposition"
                name="disposition"
                data-cy="disposition"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.paymentAmount')}
                id="payment-reconciliation-paymentAmount"
                name="paymentAmount"
                data-cy="paymentAmount"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.paymentReconciliation.paymentIdentifier')}
                id="payment-reconciliation-paymentIdentifier"
                name="paymentIdentifier"
                data-cy="paymentIdentifier"
                type="text"
              />
              <ValidatedField
                id="payment-reconciliation-paymentIssuer"
                name="paymentIssuerId"
                data-cy="paymentIssuer"
                label={translate('hcpNphiesPortalApp.paymentReconciliation.paymentIssuer')}
                type="select"
              >
                <option value="" key="0" />
                {organizations
                  ? organizations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment-reconciliation" replace color="info">
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

export default PaymentReconciliationUpdate;
