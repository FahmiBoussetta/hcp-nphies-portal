import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-eligibility-purpose-enum.reducer';
import { IListEligibilityPurposeEnum } from 'app/shared/model/list-eligibility-purpose-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListEligibilityPurposeEnumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverageEligibilityRequests = useAppSelector(state => state.coverageEligibilityRequest.entities);
  const listEligibilityPurposeEnumEntity = useAppSelector(state => state.listEligibilityPurposeEnum.entity);
  const loading = useAppSelector(state => state.listEligibilityPurposeEnum.loading);
  const updating = useAppSelector(state => state.listEligibilityPurposeEnum.updating);
  const updateSuccess = useAppSelector(state => state.listEligibilityPurposeEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-eligibility-purpose-enum');
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
      ...listEligibilityPurposeEnumEntity,
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
          ...listEligibilityPurposeEnumEntity,
          erp: 'Benefits',
          coverageEligibilityRequestId: listEligibilityPurposeEnumEntity?.coverageEligibilityRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.listEligibilityPurposeEnum.home.createOrEditLabel"
            data-cy="ListEligibilityPurposeEnumCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.home.createOrEditLabel">
              Create or edit a ListEligibilityPurposeEnum
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
                  id="list-eligibility-purpose-enum-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.listEligibilityPurposeEnum.erp')}
                id="list-eligibility-purpose-enum-erp"
                name="erp"
                data-cy="erp"
                type="select"
              >
                <option value="Benefits">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Benefits')}</option>
                <option value="Discovery">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Discovery')}</option>
                <option value="Validation">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Validation')}</option>
              </ValidatedField>
              <ValidatedField
                id="list-eligibility-purpose-enum-coverageEligibilityRequest"
                name="coverageEligibilityRequestId"
                data-cy="coverageEligibilityRequest"
                label={translate('hcpNphiesPortalApp.listEligibilityPurposeEnum.coverageEligibilityRequest')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/list-eligibility-purpose-enum"
                replace
                color="info"
              >
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

export default ListEligibilityPurposeEnumUpdate;
