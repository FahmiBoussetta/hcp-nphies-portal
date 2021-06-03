import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

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

export interface IReconciliationDetailItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReconciliationDetailItemUpdate = (props: IReconciliationDetailItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { reconciliationDetailItemEntity, claims, organizations, claimResponses, paymentReconciliations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/reconciliation-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaims();
    props.getOrganizations();
    props.getClaimResponses();
    props.getPaymentReconciliations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
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
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : reconciliationDetailItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="reconciliation-detail-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="reconciliation-detail-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="identifierLabel" for="reconciliation-detail-item-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.identifier">Identifier</Translate>
                </Label>
                <AvField id="reconciliation-detail-item-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="predecessorLabel" for="reconciliation-detail-item-predecessor">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.predecessor">Predecessor</Translate>
                </Label>
                <AvField id="reconciliation-detail-item-predecessor" data-cy="predecessor" type="text" name="predecessor" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="reconciliation-detail-item-type">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.type">Type</Translate>
                </Label>
                <AvField id="reconciliation-detail-item-type" data-cy="type" type="text" name="type" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="reconciliation-detail-item-date">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.date">Date</Translate>
                </Label>
                <AvInput
                  id="reconciliation-detail-item-date"
                  data-cy="date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.reconciliationDetailItemEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="reconciliation-detail-item-amount">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.amount">Amount</Translate>
                </Label>
                <AvField id="reconciliation-detail-item-amount" data-cy="amount" type="text" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label for="reconciliation-detail-item-request">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.request">Request</Translate>
                </Label>
                <AvInput id="reconciliation-detail-item-request" data-cy="request" type="select" className="form-control" name="requestId">
                  <option value="" key="0" />
                  {claims
                    ? claims.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reconciliation-detail-item-submitter">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.submitter">Submitter</Translate>
                </Label>
                <AvInput
                  id="reconciliation-detail-item-submitter"
                  data-cy="submitter"
                  type="select"
                  className="form-control"
                  name="submitterId"
                >
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reconciliation-detail-item-response">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.response">Response</Translate>
                </Label>
                <AvInput
                  id="reconciliation-detail-item-response"
                  data-cy="response"
                  type="select"
                  className="form-control"
                  name="responseId"
                >
                  <option value="" key="0" />
                  {claimResponses
                    ? claimResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reconciliation-detail-item-payee">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.payee">Payee</Translate>
                </Label>
                <AvInput id="reconciliation-detail-item-payee" data-cy="payee" type="select" className="form-control" name="payeeId">
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reconciliation-detail-item-paymentReconciliation">
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.paymentReconciliation">
                    Payment Reconciliation
                  </Translate>
                </Label>
                <AvInput
                  id="reconciliation-detail-item-paymentReconciliation"
                  data-cy="paymentReconciliation"
                  type="select"
                  className="form-control"
                  name="paymentReconciliationId"
                >
                  <option value="" key="0" />
                  {paymentReconciliations
                    ? paymentReconciliations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/reconciliation-detail-item" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  claims: storeState.claim.entities,
  organizations: storeState.organization.entities,
  claimResponses: storeState.claimResponse.entities,
  paymentReconciliations: storeState.paymentReconciliation.entities,
  reconciliationDetailItemEntity: storeState.reconciliationDetailItem.entity,
  loading: storeState.reconciliationDetailItem.loading,
  updating: storeState.reconciliationDetailItem.updating,
  updateSuccess: storeState.reconciliationDetailItem.updateSuccess,
});

const mapDispatchToProps = {
  getClaims,
  getOrganizations,
  getClaimResponses,
  getPaymentReconciliations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReconciliationDetailItemUpdate);
