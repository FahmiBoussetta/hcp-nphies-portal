import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { getEntities as getPaymentNotices } from 'app/entities/payment-notice/payment-notice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment-reconciliation.reducer';
import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaymentReconciliationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentReconciliationUpdate = (props: IPaymentReconciliationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { paymentReconciliationEntity, organizations, paymentNotices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payment-reconciliation');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOrganizations();
    props.getPaymentNotices();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.periodStart = convertDateTimeToServer(values.periodStart);
    values.periodEnd = convertDateTimeToServer(values.periodEnd);

    if (errors.length === 0) {
      const entity = {
        ...paymentReconciliationEntity,
        ...values,
        paymentIssuer: organizations.find(it => it.id.toString() === values.paymentIssuerId.toString()),
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
            <AvForm model={isNew ? {} : paymentReconciliationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payment-reconciliation-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payment-reconciliation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="payment-reconciliation-value">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.value">Value</Translate>
                </Label>
                <AvField id="payment-reconciliation-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="payment-reconciliation-system">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.system">System</Translate>
                </Label>
                <AvField id="payment-reconciliation-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="payment-reconciliation-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.parsed">Parsed</Translate>
                </Label>
                <AvField id="payment-reconciliation-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="periodStartLabel" for="payment-reconciliation-periodStart">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.periodStart">Period Start</Translate>
                </Label>
                <AvInput
                  id="payment-reconciliation-periodStart"
                  data-cy="periodStart"
                  type="datetime-local"
                  className="form-control"
                  name="periodStart"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentReconciliationEntity.periodStart)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="periodEndLabel" for="payment-reconciliation-periodEnd">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.periodEnd">Period End</Translate>
                </Label>
                <AvInput
                  id="payment-reconciliation-periodEnd"
                  data-cy="periodEnd"
                  type="datetime-local"
                  className="form-control"
                  name="periodEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentReconciliationEntity.periodEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="outcomeLabel" for="payment-reconciliation-outcome">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.outcome">Outcome</Translate>
                </Label>
                <AvField id="payment-reconciliation-outcome" data-cy="outcome" type="text" name="outcome" />
              </AvGroup>
              <AvGroup>
                <Label id="dispositionLabel" for="payment-reconciliation-disposition">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.disposition">Disposition</Translate>
                </Label>
                <AvField id="payment-reconciliation-disposition" data-cy="disposition" type="text" name="disposition" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentAmountLabel" for="payment-reconciliation-paymentAmount">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.paymentAmount">Payment Amount</Translate>
                </Label>
                <AvField id="payment-reconciliation-paymentAmount" data-cy="paymentAmount" type="text" name="paymentAmount" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentIdentifierLabel" for="payment-reconciliation-paymentIdentifier">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.paymentIdentifier">Payment Identifier</Translate>
                </Label>
                <AvField id="payment-reconciliation-paymentIdentifier" data-cy="paymentIdentifier" type="text" name="paymentIdentifier" />
              </AvGroup>
              <AvGroup>
                <Label for="payment-reconciliation-paymentIssuer">
                  <Translate contentKey="hcpNphiesPortalApp.paymentReconciliation.paymentIssuer">Payment Issuer</Translate>
                </Label>
                <AvInput
                  id="payment-reconciliation-paymentIssuer"
                  data-cy="paymentIssuer"
                  type="select"
                  className="form-control"
                  name="paymentIssuerId"
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
              <Button tag={Link} id="cancel-save" to="/payment-reconciliation" replace color="info">
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
  organizations: storeState.organization.entities,
  paymentNotices: storeState.paymentNotice.entities,
  paymentReconciliationEntity: storeState.paymentReconciliation.entity,
  loading: storeState.paymentReconciliation.loading,
  updating: storeState.paymentReconciliation.updating,
  updateSuccess: storeState.paymentReconciliation.updateSuccess,
});

const mapDispatchToProps = {
  getOrganizations,
  getPaymentNotices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentReconciliationUpdate);
