import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cost-to-beneficiary-component.reducer';
import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CostToBeneficiaryComponentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverages = useAppSelector(state => state.coverage.entities);
  const costToBeneficiaryComponentEntity = useAppSelector(state => state.costToBeneficiaryComponent.entity);
  const loading = useAppSelector(state => state.costToBeneficiaryComponent.loading);
  const updating = useAppSelector(state => state.costToBeneficiaryComponent.updating);
  const updateSuccess = useAppSelector(state => state.costToBeneficiaryComponent.updateSuccess);

  const handleClose = () => {
    props.history.push('/cost-to-beneficiary-component');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...costToBeneficiaryComponentEntity,
      ...values,
      coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
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
          ...costToBeneficiaryComponentEntity,
          type: 'Gpvisit',
          coverageId: costToBeneficiaryComponentEntity?.coverage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.costToBeneficiaryComponent.home.createOrEditLabel"
            data-cy="CostToBeneficiaryComponentCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.createOrEditLabel">
              Create or edit a CostToBeneficiaryComponent
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
                  id="cost-to-beneficiary-component-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.costToBeneficiaryComponent.type')}
                id="cost-to-beneficiary-component-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="Gpvisit">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Gpvisit')}</option>
                <option value="Spvisit">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Spvisit')}</option>
                <option value="Copaypct">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Copaypct')}</option>
                <option value="Copay">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Copay')}</option>
                <option value="Deductible">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Deductible')}</option>
                <option value="Maxoutofpocket">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Maxoutofpocket')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.costToBeneficiaryComponent.isMoney')}
                id="cost-to-beneficiary-component-isMoney"
                name="isMoney"
                data-cy="isMoney"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.costToBeneficiaryComponent.value')}
                id="cost-to-beneficiary-component-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="cost-to-beneficiary-component-coverage"
                name="coverageId"
                data-cy="coverage"
                label={translate('hcpNphiesPortalApp.costToBeneficiaryComponent.coverage')}
                type="select"
              >
                <option value="" key="0" />
                {coverages
                  ? coverages.map(otherEntity => (
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
                to="/cost-to-beneficiary-component"
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

export default CostToBeneficiaryComponentUpdate;
