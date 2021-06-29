import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';
import { getEntities as getPaymentReconciliations } from 'app/entities/payment-reconciliation/payment-reconciliation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './reconciliation-detail-item.reducer';
import { IReconciliationDetailItem } from 'app/shared/model/reconciliation-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReconciliationDetailItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const claims = useAppSelector(state => state.claim.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const claimResponses = useAppSelector(state => state.claimResponse.entities);
  const paymentReconciliations = useAppSelector(state => state.paymentReconciliation.entities);
  const reconciliationDetailItemEntity = useAppSelector(state => state.reconciliationDetailItem.entity);
  const loading = useAppSelector(state => state.reconciliationDetailItem.loading);
  const updating = useAppSelector(state => state.reconciliationDetailItem.updating);
  const updateSuccess = useAppSelector(state => state.reconciliationDetailItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/reconciliation-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClaims({}));
    dispatch(getOrganizations({}));
    dispatch(getClaimResponses({}));
    dispatch(getPaymentReconciliations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...reconciliationDetailItemEntity,
      ...values,
      request: claims.find(it => it.id.toString() === values.requestId.toString()),
      submitter: organizations.find(it => it.id.toString() === values.submitterId.toString()),
      payee: organizations.find(it => it.id.toString() === values.payeeId.toString()),
      response: claimResponses.find(it => it.id.toString() === values.responseId.toString()),
      paymentReconciliation: paymentReconciliations.find(it => it.id.toString() === values.paymentReconciliationId.toString()),
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
          date: displayDefaultDateTime(),
        }
      : {
          ...reconciliationDetailItemEntity,
          date: convertDateTimeFromServer(reconciliationDetailItemEntity.date),
          requestId: reconciliationDetailItemEntity?.request?.id,
          submitterId: reconciliationDetailItemEntity?.submitter?.id,
          responseId: reconciliationDetailItemEntity?.response?.id,
          payeeId: reconciliationDetailItemEntity?.payee?.id,
          paymentReconciliationId: reconciliationDetailItemEntity?.paymentReconciliation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.reconciliationDetailItem.home.createOrEditLabel" data-cy="ReconciliationDetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.home.createOrEditLabel">
              Create or edit a ReconciliationDetailItem
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
                  id="reconciliation-detail-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.identifier')}
                id="reconciliation-detail-item-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.predecessor')}
                id="reconciliation-detail-item-predecessor"
                name="predecessor"
                data-cy="predecessor"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.type')}
                id="reconciliation-detail-item-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.date')}
                id="reconciliation-detail-item-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.amount')}
                id="reconciliation-detail-item-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                id="reconciliation-detail-item-request"
                name="requestId"
                data-cy="request"
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.request')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reconciliation-detail-item-submitter"
                name="submitterId"
                data-cy="submitter"
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.submitter')}
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
              <ValidatedField
                id="reconciliation-detail-item-response"
                name="responseId"
                data-cy="response"
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.response')}
                type="select"
              >
                <option value="" key="0" />
                {claimResponses
                  ? claimResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reconciliation-detail-item-payee"
                name="payeeId"
                data-cy="payee"
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.payee')}
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
              <ValidatedField
                id="reconciliation-detail-item-paymentReconciliation"
                name="paymentReconciliationId"
                data-cy="paymentReconciliation"
                label={translate('hcpNphiesPortalApp.reconciliationDetailItem.paymentReconciliation')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reconciliation-detail-item" replace color="info">
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

export default ReconciliationDetailItemUpdate;
