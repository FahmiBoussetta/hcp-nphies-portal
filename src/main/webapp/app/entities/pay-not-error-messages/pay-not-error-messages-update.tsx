import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { getEntities as getPaymentNotices } from 'app/entities/payment-notice/payment-notice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pay-not-error-messages.reducer';
import { IPayNotErrorMessages } from 'app/shared/model/pay-not-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayNotErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const paymentNotices = useAppSelector(state => state.paymentNotice.entities);
  const payNotErrorMessagesEntity = useAppSelector(state => state.payNotErrorMessages.entity);
  const loading = useAppSelector(state => state.payNotErrorMessages.loading);
  const updating = useAppSelector(state => state.payNotErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.payNotErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/pay-not-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPaymentNotices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...payNotErrorMessagesEntity,
      ...values,
      paymentNotice: paymentNotices.find(it => it.id.toString() === values.paymentNoticeId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...payNotErrorMessagesEntity,
          paymentNoticeId: payNotErrorMessagesEntity?.paymentNotice?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="pay-not-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.payNotErrorMessages.message')}
                id="pay-not-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="pay-not-error-messages-paymentNotice"
                name="paymentNoticeId"
                data-cy="paymentNotice"
                label={translate('hcpNphiesPortalApp.payNotErrorMessages.paymentNotice')}
                type="select"
              >
                <option value="" key="0" />
                {paymentNotices
                  ? paymentNotices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pay-not-error-messages" replace color="info">
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

export default PayNotErrorMessagesUpdate;
