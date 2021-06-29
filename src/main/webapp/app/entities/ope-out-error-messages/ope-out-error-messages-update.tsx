import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOperationOutcome } from 'app/shared/model/operation-outcome.model';
import { getEntities as getOperationOutcomes } from 'app/entities/operation-outcome/operation-outcome.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ope-out-error-messages.reducer';
import { IOpeOutErrorMessages } from 'app/shared/model/ope-out-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OpeOutErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const operationOutcomes = useAppSelector(state => state.operationOutcome.entities);
  const opeOutErrorMessagesEntity = useAppSelector(state => state.opeOutErrorMessages.entity);
  const loading = useAppSelector(state => state.opeOutErrorMessages.loading);
  const updating = useAppSelector(state => state.opeOutErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.opeOutErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/ope-out-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOperationOutcomes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...opeOutErrorMessagesEntity,
      ...values,
      operationOutcome: operationOutcomes.find(it => it.id.toString() === values.operationOutcomeId.toString()),
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
          ...opeOutErrorMessagesEntity,
          operationOutcomeId: opeOutErrorMessagesEntity?.operationOutcome?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.opeOutErrorMessages.home.createOrEditLabel" data-cy="OpeOutErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.createOrEditLabel">
              Create or edit a OpeOutErrorMessages
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
                  id="ope-out-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.opeOutErrorMessages.message')}
                id="ope-out-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="ope-out-error-messages-operationOutcome"
                name="operationOutcomeId"
                data-cy="operationOutcome"
                label={translate('hcpNphiesPortalApp.opeOutErrorMessages.operationOutcome')}
                type="select"
              >
                <option value="" key="0" />
                {operationOutcomes
                  ? operationOutcomes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ope-out-error-messages" replace color="info">
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

export default OpeOutErrorMessagesUpdate;
