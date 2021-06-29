import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cov-eli-error-messages.reducer';
import { ICovEliErrorMessages } from 'app/shared/model/cov-eli-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CovEliErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverageEligibilityRequests = useAppSelector(state => state.coverageEligibilityRequest.entities);
  const covEliErrorMessagesEntity = useAppSelector(state => state.covEliErrorMessages.entity);
  const loading = useAppSelector(state => state.covEliErrorMessages.loading);
  const updating = useAppSelector(state => state.covEliErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.covEliErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/cov-eli-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverageEligibilityRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...covEliErrorMessagesEntity,
      ...values,
      coverageEligibilityRequest: coverageEligibilityRequests.find(
        it => it.id.toString() === values.coverageEligibilityRequestId.toString()
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
          ...covEliErrorMessagesEntity,
          coverageEligibilityRequestId: covEliErrorMessagesEntity?.coverageEligibilityRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.covEliErrorMessages.home.createOrEditLabel" data-cy="CovEliErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.createOrEditLabel">
              Create or edit a CovEliErrorMessages
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
                  id="cov-eli-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.covEliErrorMessages.message')}
                id="cov-eli-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="cov-eli-error-messages-coverageEligibilityRequest"
                name="coverageEligibilityRequestId"
                data-cy="coverageEligibilityRequest"
                label={translate('hcpNphiesPortalApp.covEliErrorMessages.coverageEligibilityRequest')}
                type="select"
              >
                <option value="" key="0" />
                {coverageEligibilityRequests
                  ? coverageEligibilityRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cov-eli-error-messages" replace color="info">
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

export default CovEliErrorMessagesUpdate;
