import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './operation-outcome.reducer';
import { IOperationOutcome } from 'app/shared/model/operation-outcome.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OperationOutcomeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const operationOutcomeEntity = useAppSelector(state => state.operationOutcome.entity);
  const loading = useAppSelector(state => state.operationOutcome.loading);
  const updating = useAppSelector(state => state.operationOutcome.updating);
  const updateSuccess = useAppSelector(state => state.operationOutcome.updateSuccess);

  const handleClose = () => {
    props.history.push('/operation-outcome');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...operationOutcomeEntity,
      ...values,
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
          ...operationOutcomeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.operationOutcome.home.createOrEditLabel" data-cy="OperationOutcomeCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.operationOutcome.home.createOrEditLabel">Create or edit a OperationOutcome</Translate>
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
                  id="operation-outcome-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.operationOutcome.value')}
                id="operation-outcome-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.operationOutcome.system')}
                id="operation-outcome-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.operationOutcome.parsed')}
                id="operation-outcome-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/operation-outcome" replace color="info">
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

export default OperationOutcomeUpdate;
