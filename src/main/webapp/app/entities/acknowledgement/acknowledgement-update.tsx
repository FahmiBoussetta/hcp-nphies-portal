import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './acknowledgement.reducer';
import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AcknowledgementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const acknowledgementEntity = useAppSelector(state => state.acknowledgement.entity);
  const loading = useAppSelector(state => state.acknowledgement.loading);
  const updating = useAppSelector(state => state.acknowledgement.updating);
  const updateSuccess = useAppSelector(state => state.acknowledgement.updateSuccess);

  const handleClose = () => {
    props.history.push('/acknowledgement');
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
      ...acknowledgementEntity,
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
          ...acknowledgementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.acknowledgement.home.createOrEditLabel" data-cy="AcknowledgementCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.createOrEditLabel">Create or edit a Acknowledgement</Translate>
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
                  id="acknowledgement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.acknowledgement.value')}
                id="acknowledgement-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.acknowledgement.system')}
                id="acknowledgement-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.acknowledgement.parsed')}
                id="acknowledgement-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/acknowledgement" replace color="info">
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

export default AcknowledgementUpdate;
