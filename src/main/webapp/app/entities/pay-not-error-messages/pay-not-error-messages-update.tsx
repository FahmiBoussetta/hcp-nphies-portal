import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { getEntities as getPaymentNotices } from 'app/entities/payment-notice/payment-notice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pay-not-error-messages.reducer';
import { IPayNotErrorMessages } from 'app/shared/model/pay-not-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPayNotErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayNotErrorMessagesUpdate = (props: IPayNotErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { payNotErrorMessagesEntity, paymentNotices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pay-not-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPaymentNotices();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...payNotErrorMessagesEntity,
        ...values,
        paymentNotice: paymentNotices.find(it => it.id.toString() === values.paymentNoticeId.toString()),
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
          <h2 id="hcpNphiesPortalApp.payNotErrorMessages.home.createOrEditLabel" data-cy="PayNotErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.home.createOrEditLabel">
              Create or edit a PayNotErrorMessages
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : payNotErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pay-not-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pay-not-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="pay-not-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="pay-not-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="pay-not-error-messages-paymentNotice">
                  <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.paymentNotice">Payment Notice</Translate>
                </Label>
                <AvInput
                  id="pay-not-error-messages-paymentNotice"
                  data-cy="paymentNotice"
                  type="select"
                  className="form-control"
                  name="paymentNoticeId"
                >
                  <option value="" key="0" />
                  {paymentNotices
                    ? paymentNotices.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pay-not-error-messages" replace color="info">
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
  paymentNotices: storeState.paymentNotice.entities,
  payNotErrorMessagesEntity: storeState.payNotErrorMessages.entity,
  loading: storeState.payNotErrorMessages.loading,
  updating: storeState.payNotErrorMessages.updating,
  updateSuccess: storeState.payNotErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getPaymentNotices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayNotErrorMessagesUpdate);
