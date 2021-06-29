import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './claim-response.reducer';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClaimResponseUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const claimResponseEntity = useAppSelector(state => state.claimResponse.entity);
  const loading = useAppSelector(state => state.claimResponse.loading);
  const updating = useAppSelector(state => state.claimResponse.updating);
  const updateSuccess = useAppSelector(state => state.claimResponse.updateSuccess);

  const handleClose = () => {
    props.history.push('/claim-response');
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
      ...claimResponseEntity,
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
          ...claimResponseEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.claimResponse.home.createOrEditLabel" data-cy="ClaimResponseCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.claimResponse.home.createOrEditLabel">Create or edit a ClaimResponse</Translate>
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
                  id="claim-response-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claimResponse.value')}
                id="claim-response-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claimResponse.system')}
                id="claim-response-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claimResponse.parsed')}
                id="claim-response-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claimResponse.outcome')}
                id="claim-response-outcome"
                name="outcome"
                data-cy="outcome"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim-response" replace color="info">
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

export default ClaimResponseUpdate;
