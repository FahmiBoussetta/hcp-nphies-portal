import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';
import { getEntities as getPaymentReconciliations } from 'app/entities/payment-reconciliation/payment-reconciliation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment-notice.reducer';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaymentNoticeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentNoticeUpdate = (props: IPaymentNoticeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { paymentNoticeEntity, paymentReconciliations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payment-notice');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPaymentReconciliations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.paymentDate = convertDateTimeToServer(values.paymentDate);

    if (errors.length === 0) {
      const entity = {
        ...paymentNoticeEntity,
        ...values,
        payment: paymentReconciliations.find(it => it.id.toString() === values.paymentId.toString()),
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
            <AvForm model={isNew ? {} : paymentNoticeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payment-notice-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payment-notice-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="payment-notice-guid">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.guid">Guid</Translate>
                </Label>
                <AvField id="payment-notice-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="payment-notice-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.parsed">Parsed</Translate>
                </Label>
                <AvField id="payment-notice-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="payment-notice-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.identifier">Identifier</Translate>
                </Label>
                <AvField id="payment-notice-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentDateLabel" for="payment-notice-paymentDate">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentDate">Payment Date</Translate>
                </Label>
                <AvInput
                  id="payment-notice-paymentDate"
                  data-cy="paymentDate"
                  type="datetime-local"
                  className="form-control"
                  name="paymentDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentNoticeEntity.paymentDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="payment-notice-amount">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.amount">Amount</Translate>
                </Label>
                <AvField id="payment-notice-amount" data-cy="amount" type="text" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentStatusLabel" for="payment-notice-paymentStatus">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentStatus">Payment Status</Translate>
                </Label>
                <AvInput
                  id="payment-notice-paymentStatus"
                  data-cy="paymentStatus"
                  type="select"
                  className="form-control"
                  name="paymentStatus"
                  value={(!isNew && paymentNoticeEntity.paymentStatus) || 'Paid'}
                >
                  <option value="Paid">{translate('hcpNphiesPortalApp.PaymentStatusEnum.Paid')}</option>
                  <option value="Cleared">{translate('hcpNphiesPortalApp.PaymentStatusEnum.Cleared')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="payment-notice-payment">
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.payment">Payment</Translate>
                </Label>
                <AvInput id="payment-notice-payment" data-cy="payment" type="select" className="form-control" name="paymentId">
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
              <Button tag={Link} id="cancel-save" to="/payment-notice" replace color="info">
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
  paymentReconciliations: storeState.paymentReconciliation.entities,
  paymentNoticeEntity: storeState.paymentNotice.entity,
  loading: storeState.paymentNotice.loading,
  updating: storeState.paymentNotice.updating,
  updateSuccess: storeState.paymentNotice.updateSuccess,
});

const mapDispatchToProps = {
  getPaymentReconciliations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentNoticeUpdate);
