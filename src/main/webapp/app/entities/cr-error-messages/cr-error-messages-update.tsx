import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cr-error-messages.reducer';
import { ICRErrorMessages } from 'app/shared/model/cr-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CRErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const claimResponses = useAppSelector(state => state.claimResponse.entities);
  const cRErrorMessagesEntity = useAppSelector(state => state.cRErrorMessages.entity);
  const loading = useAppSelector(state => state.cRErrorMessages.loading);
  const updating = useAppSelector(state => state.cRErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.cRErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/cr-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClaimResponses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cRErrorMessagesEntity,
      ...values,
      claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
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
          ...cRErrorMessagesEntity,
          claimResponseId: cRErrorMessagesEntity?.claimResponse?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.cRErrorMessages.home.createOrEditLabel" data-cy="CRErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.home.createOrEditLabel">Create or edit a CRErrorMessages</Translate>
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
                  id="cr-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.cRErrorMessages.message')}
                id="cr-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="cr-error-messages-claimResponse"
                name="claimResponseId"
                data-cy="claimResponse"
                label={translate('hcpNphiesPortalApp.cRErrorMessages.claimResponse')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cr-error-messages" replace color="info">
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

export default CRErrorMessagesUpdate;
