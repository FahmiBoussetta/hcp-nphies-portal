import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { getEntities as getCoverageEligibilityResponses } from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cov-eli-resp-error-messages.reducer';
import { ICovEliRespErrorMessages } from 'app/shared/model/cov-eli-resp-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CovEliRespErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverageEligibilityResponses = useAppSelector(state => state.coverageEligibilityResponse.entities);
  const covEliRespErrorMessagesEntity = useAppSelector(state => state.covEliRespErrorMessages.entity);
  const loading = useAppSelector(state => state.covEliRespErrorMessages.loading);
  const updating = useAppSelector(state => state.covEliRespErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.covEliRespErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/cov-eli-resp-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverageEligibilityResponses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...covEliRespErrorMessagesEntity,
      ...values,
      coverageEligibilityResponse: coverageEligibilityResponses.find(
        it => it.id.toString() === values.coverageEligibilityResponseId.toString()
      ),
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
          ...covEliRespErrorMessagesEntity,
          coverageEligibilityResponseId: covEliRespErrorMessagesEntity?.coverageEligibilityResponse?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.covEliRespErrorMessages.home.createOrEditLabel" data-cy="CovEliRespErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.home.createOrEditLabel">
              Create or edit a CovEliRespErrorMessages
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
                  id="cov-eli-resp-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.covEliRespErrorMessages.message')}
                id="cov-eli-resp-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="cov-eli-resp-error-messages-coverageEligibilityResponse"
                name="coverageEligibilityResponseId"
                data-cy="coverageEligibilityResponse"
                label={translate('hcpNphiesPortalApp.covEliRespErrorMessages.coverageEligibilityResponse')}
                type="select"
              >
                <option value="" key="0" />
                {coverageEligibilityResponses
                  ? coverageEligibilityResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cov-eli-resp-error-messages" replace color="info">
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

export default CovEliRespErrorMessagesUpdate;
